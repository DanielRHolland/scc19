
### gRPC

1. Make sure protoc already installed
2. Add go plugin for protoc: `go get github.com/golang/protobuf/protoc-gen-go@v1.3`
  1. Add to path: `export PATH="$PATH:$(go env GOPATH)/bin"`
4. Install grpc for go: `go get google.golang.org/grpc`
5. Put `curr.proto` in `go/src/uk_co_danrh_protos_curr/`
6. Compile `curr.proto`: `protoc --go_out=plugins=grpc:. --go_opt=paths=source_relative curr.proto`
7. Now compile and run the currency converter

### REST

Edit `main` method of `CurrencyService.go`, uncommenting lines 86-88 (flag fetching & parsing, and `setupRestApi`), commenting line 89 (`setupGrpcApi()`). 
