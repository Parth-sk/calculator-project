
stage('Deploy') {
    steps {
        sh '''
            printf 'DOCKERHUB_USERNAME=parthsk\\nIMAGE_TAG=%s\\n' \
                "$BUILD_NUMBER" > "$WORKSPACE/.env"

            HOST_WORKSPACE="/var/lib/docker/volumes/jenkins_jenkins_home/_data${WORKSPACE#/var/jenkins_home}"

            docker compose \
                --env-file "$WORKSPACE/.env" \
                -p "$COMPOSE_PROJECT" \
                --project-directory "$HOST_WORKSPACE" \
                -f "$WORKSPACE/docker-compose.yml" \
                pull calculator

            docker compose \
                --env-file "$WORKSPACE/.env" \
                -p "$COMPOSE_PROJECT" \
                --project-directory "$HOST_WORKSPACE" \
                -f "$WORKSPACE/docker-compose.yml" \
                up -d --remove-orphans

            docker run --rm \
                --network calculator-project_calculator-network \
                curlimages/curl:latest \
                -fsS http://nginx/actuator/health
        '''
    }
}
