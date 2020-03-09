#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Image') {
            when {
                branch 'master'
            }
            steps {
                script {
                    // Deploy server
                    sh 'docker build . -t pdb'
                    
                    def result = sh(script: """docker run -d --rm --name pdb-app -p 3000:3000 \
                        -v $PWD/target/pdb-0.0.3-SNAPSHOT.war:/pdb-data/pdb.war \
                        -e "SPRING_PROFILES_ACTIVE=docker" \
                        --link pdb-db:postgres pdb""",
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
        success {
            //zpost(0)
        }
        unstable {
            //zpost(1)
        }
        failure {
            //zpost(2)
        }
    }
    }
