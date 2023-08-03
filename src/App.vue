<template>
  <div id="app">
    <h2>Vue.js WebSocket Test</h2>
    <span>{{ connectStatusMessage }}</span>

    <h2>Message:</h2>
    <textarea v-model="messageContext"></textarea>
    <button v-on:click="sendMessage">Send Message</button>

    <h2>Message List</h2>
    <div v-for="(item, idx) in topicGreetingList" :key="idx">
      <p>{{ item }}</p>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import StompClient from "stompjs";

export default {
  name: "App",
  data: () => {
    return {
      connection: null,
      stompClient: null,
      connectStatusMessage: "",
      messageContext: "hello",
      topicGreetingList: [],
    };
  },
  methods: {
    disconnect() {
      if (this.tompClient !== null) {
        this.stompClient.disconnect();
      }
      console.log("Disconnected");
    },

    subscribeTopicGreetings(greeting) {
      console.log("⭐️Get TopicGreeting");
      let message = JSON.parse(greeting.body).content;
      console.log(message);
      console.log(greeting);
      this.topicGreetingList.push(this.getDateString(new Date()) + " → " + message);
    },

    sendMessage() {
      this.stompClient.send(
        "/app/hello",
        {},
        JSON.stringify({
          name: "vue.js Websockets Test!",
          message: this.messageContext,
        })
      );
    },
    getDateString(date) {
      const yyyymmdd = new Intl.DateTimeFormat(undefined, {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
        second: "2-digit",
      });

      return yyyymmdd.format(date)
    },
  },
  created() {
    let connection = new SockJS("http://localhost:8080/gs-guide-websocket");
    this.stompClient = StompClient.over(connection);
    this.stompClient.connect({}, (frame) => {
      // 接続に成功したら
      console.log("Connected: " + frame);
      this.connectStatusMessage = "Connected: " + frame;

      // Subscribeの設定 メッセージを受け取った時に実行されるメソッドを渡すので（）はつけない
      this.stompClient.subscribe(
        "/topic/greetings",
        this.subscribeTopicGreetings
      );
    });
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
div {
  margin: 30px;
}
button {
  margin: 30px;
}
</style>
