const express = require("express");
const app  = express();
const port = 3000;

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

// HTTPサーバを起動する
app.listen(port, () => {
  console.log(`listening at http://localhost:${port}`);
});