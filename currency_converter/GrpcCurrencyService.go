package main

import (
	"context"
	"log"
	"net"
	grpc "google.golang.org/grpc"
        pb "uk_co_danrh_protos_curr"
)

const (
	port = ":50051"
)




// server is used to implement helloworld.GreeterServer.
type server struct {
	pb.UnimplementedCurrencyServer
}

// SayHello implements helloworld.GreeterServer
func (s *server) GetSymbols(ctx context.Context, in *pb.SearchOptions) (*pb.SymbolList, error) {
	log.Printf("Received %d", in.GetNumber())
        return &pb.SymbolList{Symbol: getCurrencyCodes()}, nil
}

func (s *server) GetRate(ctx context.Context, in *pb.ConversionSymbols) (*pb.Rate, error) {
        log.Printf("Conversion from %s to %s", in.GetCurrencyFrom(), in.GetCurrencyTo())
        rate, err := calcConvRate(in.GetCurrencyFrom(), in.GetCurrencyTo())
        return &pb.Rate{Rate: rate}, err
}

func setupGrpcApi() {
	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterCurrencyServer(s, &server{})
        if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
