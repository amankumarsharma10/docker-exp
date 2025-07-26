#!/bin/sh

# Host and port from env or defaults
HOST="${MYSQL_HOST:-mysql-db}"
PORT="${MYSQL_PORT:-3306}"

echo "Waiting for MySQL at $HOST:$PORT..."

# Wait until MySQL is reachable
while ! nc -z "$HOST" "$PORT"; do
  sleep 1
done

echo "MySQL is up - launching Spring Boot application"

# Run the application (forward all arguments)
exec "$@"
