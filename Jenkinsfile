#!/usr/bin/env groovy

pipeline {
    agent any

    stages {
        stage('Docker Image') {
            when {
                branch 'master'
            }
            steps {
                script {
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
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/build/test-results/junit-platform/TEST-*.xml'
        }
    }
}
