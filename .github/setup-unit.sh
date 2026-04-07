docker compose -f docker-compose-ci.yml up -d

until [ "$(docker inspect --format '{{json .State.Health.Status}}' plugin-skopeo-registry 2>/dev/null | tr -d '"')" = "healthy" ]; do
  sleep 2
done
