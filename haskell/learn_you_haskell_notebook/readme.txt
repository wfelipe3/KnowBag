docker pull gibiansky/ihaskell:latest

docker run -it --volume $(pwd):/notebooks --publish 8888:8888 gibiansky/ihaskell:latest
