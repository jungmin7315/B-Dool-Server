pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials'
        REPO_URL = 'https://github.com/B-Dool/B-Dool-Server.git'
        DOCKER_HUB_URL = 'gusdn0413'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: "${REPO_URL}"
            }
        }

        stage('Build and Push Modules') {
            parallel {
                stage('Auth Service') {
                    steps {
                        dir('auth-service') {
                            sh './gradlew clean build'
                            script {
                                def imageName = "${DOCKER_HUB_URL}/bdool-auth-service"
                                docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                                    docker.build("${imageName}:latest", '.').push()
                                }
                            }
                        }
                    }
                }
                stage('Member Hub Service') {
                    steps {
                        dir('member-hub-service') {
                            sh './gradlew clean build'
                            script {
                                def imageName = "${DOCKER_HUB_URL}/bdool-member-service"
                                docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                                    docker.build("${imageName}:latest", '.').push()
                                }
                            }
                        }
                    }
                }
                stage('Notification Service') {
                    steps {
                        dir('notification-service') {
                            sh './gradlew clean build'
                            script {
                                def imageName = "${DOCKER_HUB_URL}/bdool-notification-service"
                                docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                                    docker.build("${imageName}:latest", '.').push()
                                }
                            }
                        }
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying all services...'
            }
        }
    }

    post {
        success {
            echo '모든 모듈이 성공적으로 빌드 및 푸시되었습니다.'
        }
        failure {
            echo '빌드 또는 푸시 실패!'
        }
    }
}