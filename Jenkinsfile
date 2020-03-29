#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Test and Build') {
            when {
                not {
                    anyOf {
                        branch 'master';
                        branch 'develop'
                    }
                }
            }
            
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Docker Image Master') {
            when {
                branch 'master'
            }
            steps {
                script {
                    sh 'docker ps -q --filter name="pdb-app" | xargs -r docker stop'
                    // Deploy server
                    sh 'docker build . -t pdb'
                    
                    def result = sh(script: """docker run -d --rm --name pdb-app -p 3000:3000 \
                        -e "SPRING_PROFILES_ACTIVE=docker" \
                        --link pdb-db:postgres pdb""",
                        returnStdout: true)
                    println(result)
                }
            }
        }

        stage('Docker Image develop') {
            when {
                branch 'develop'
            }
            steps {
                script {
                    sh 'docker ps -q --filter name="pdb-dev-app" | xargs -r docker stop'
                    // Deploy server
                    sh 'docker build . -t pdb-dev'

                    def result = sh(script: """docker run -d --rm --name pdb-dev-app -p 4000:3000 \
                        -e "SPRING_PROFILES_ACTIVE=docker_dev" \
                        --link pdb-dev-db:postgres pdb-dev""",
                            returnStdout: true)
                    println(result)
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/build/test-results/junit-platform/TEST-*.xml'
        }
    }
}
