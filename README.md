# Spring Cloud Config Server + push refresh (Spring Cloud Bus)

```
 git push -> GitHub/GitLab webhook -> config-server /monitor
                                          | RefreshRemoteApplicationEvent
                                          v
                                      RabbitMQ (springCloudBus)
                                          |
                          +---------------+---------------+
                    orders-service #1             orders-service #2   -> new values, no restart
```

## Run it
```bash
docker compose up -d                                   # RabbitMQ

cd config-repo && git init -b main && git add . && git commit -m "init" && cd ..

cd config-server && mvn spring-boot:run                # :8888
cd orders-service && mvn spring-boot:run               # :8081 (other terminal)
```

Check:
```bash
curl -u config:s3cret http://localhost:8888/orders-service/dev
curl http://localhost:8081/config
```

## Push a change
1. Edit `config-repo/orders-service.yml` (e.g. `max-items-per-order: 50`) and commit.
2. Simulate the Git webhook:
```bash
curl -X POST http://localhost:8888/monitor \
     -H 'Content-Type: application/x-www-form-urlencoded' \
     -d 'path=orders-service'
```
3. `curl http://localhost:8081/config` shows the new value, no restart.

Other options:
- Refresh all apps: `curl -u config:s3cret -X POST http://localhost:8888/actuator/busrefresh`
- One app only: `.../actuator/busrefresh/orders-service:**`

## Real webhook (GitHub / GitLab / Bitbucket)
Add a webhook on the config repo pointing to `https://<config-server-host>/monitor`
(JSON). The monitor module detects the provider and notifies only the apps whose
files changed. `/monitor` is open in `SecurityConfig`; protect it with an IP
allow-list or your gateway.

## Notes
- `@Value` fields need `@RefreshScope`; `@ConfigurationProperties` with setters rebinds automatically.
- Use a strong `ENCRYPT_KEY` and store secrets as `{cipher}...`.
- Swap `bus-amqp` for `bus-kafka` to use Kafka.
