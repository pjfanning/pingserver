FROM azul/zulu-openjdk-alpine:17.0.0

RUN apk add --update \
    openssl \
    ca-certificates \
    bash \
    && rm -rf /var/cache/apk/*

WORKDIR /root

ADD entrypoint.sh /entrypoint.sh
ADD target/universal/pingserver-0.1.1.tgz .

RUN chmod +x /entrypoint.sh

ENTRYPOINT "/entrypoint.sh"
