const express = require("express");
const app  = express();
var http = require('http').Server(app);
const io = require('socket.io')(http);
const port = process.env.PORT || 3000;


app.get("/sample1", (req, res) =>{
  // res.send('Hello World sample1!');
  console.log("/sample1 へアクセスがありました");
  const course = {
      result:"true",
      message: "success"
  };
  res.send(course);
});

// ルーティングの設定
app.get("/", (req, res) =>{
  res.send('Hello World!');
  console.log("/ へアクセスがありました");
});

/**
 * Socket管理
 */
io.on('connection',function(socket){
  console.log('connected');
  // 入室
    socket.on('server_join_room',function(msg){
      console.log('server_join_room: ' + msg);
      io.emit('client_join_room',msg);
    });

    // 退室
    socket.on('server_exit_room',function(msg){
      console.log('server_exit_room: ' + msg);
      io.emit('client_exit_room',msg);
    });

    // メッセージ送受信
    socket.on('server_receive_message',function(msg){
      console.log('server_receive_message: ' + msg);
      io.emit('client_receive_message',msg);
    });
});

// HTTPサーバを起動する
http.listen(port, () => {
  console.log(`listening at http://localhost:${port}`);
});