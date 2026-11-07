pipeline {
    agent any

    options {
        disableConcurrentBuilds()
    }

    tools {
        maven 'Maven-3.9'
    }

    environment {
        DOCKER_IMAGE = 'parthsk/calculator-api'
        COMPOSE_PROJECT = 'calculator-project'
    }

    triggers {
        pollSCM('H/5 * * * *')
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
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$BUILD_NUMBER .'
            }
        }

        stage('Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push "$DOCKER_IMAGE:$BUILD_NUMBER"
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
    }
    post {
        success {
            emailext(
                subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Pipeline completed successfully.\n\nBuild: ${env.BUILD_URL}",
                to: 'parthsivakumar@gmail.com'
            )
        }

        failure {
            emailext(
                subject: "FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Pipeline failed.\n\nCheck console output: ${env.BUILD_URL}",
                to: 'parthsivakumar@gmail.com'
            )
        }
    }
}