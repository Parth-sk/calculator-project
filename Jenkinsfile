
pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    environment {
        IMAGE_NAME = 'parthsk/calculator-api'
        COMPOSE_PROJECT = 'calculator-project'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit allowEmptyResults: true,
                          testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$BUILD_NUMBER .'
            }
        }

        stage('Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_TOKEN'
                )]) {
                    sh '''
                        echo "$DOCKER_TOKEN" | docker login \
                            -u "$DOCKER_USER" --password-stdin
                        docker push $IMAGE_NAME:$BUILD_NUMBER
                        docker logout
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    printf 'DOCKERHUB_USERNAME=parthsk\\nIMAGE_TAG=%s\\n' \
                        "$BUILD_NUMBER" > .env

                    docker compose -p "$COMPOSE_PROJECT" \
                        --project-directory "$WORKSPACE" \
                        -f "$WORKSPACE/docker-compose.yml" pull calculator

                    docker compose -p "$COMPOSE_PROJECT" \
                        --project-directory "$WORKSPACE" \
                        -f "$WORKSPACE/docker-compose.yml" up -d --remove-orphans

                    docker run --rm \
                        --network calculator-project_calculator-network \
                        curlimages/curl:latest \
                        -fsS http://nginx/actuator/health
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully.'
        }
        failure {
            echo 'Pipeline failed. Check the stage logs.'
        }
    }
}
