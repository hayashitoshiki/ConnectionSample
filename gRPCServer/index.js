const protoLoader = require('@grpc/proto-loader');
// インストールしたパッケージに合わせる
// const grpc = require('grpc');
const grpc = require('@grpc/grpc-js');
const PROTO_PATH = __dirname + '/sample.proto'

// 定義ファイル(.protoファイル)の読み込み
const packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    });
const hello_proto = grpc.loadPackageDefinition(packageDefinition).com.myapp.connectionsample.grpc.api;

/**
 * sayHelloメソッド
 */
function Sum(call, callback) {
  var c = call.request.a + call.request.b
  callback(null, {message: c});
}

/**
 * mainメソッド
 */
function main() {
  // サーバのインスタンスを生成
  const server = new grpc.Server();
  // サーバがGreeterサービスのリクエストを受け取るようにする
  server.addService(hello_proto.GrpcLessonService.service, {Sum: Sum});
  // クライアントのリクエストをリッスンする(外部からのアクセスに備えて待機する)ためのアドレスとポートを指定
  server.bindAsync('127.0.0.1:3000', grpc.ServerCredentials.createInsecure(), () => {
    // サーバを起動する
    server.start();
  });
}

main();