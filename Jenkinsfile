pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './gradlew build'
                archiveArtifacts artifacts: 'build/libs/*.jar', allowEmptyArchive: false
            }
        }

        stage('Test Results') {
            steps {
                junit 'build/test-results/test/*.xml'
            }
        }

        stage('Check Test Coverage (Optional)') {
            steps {
                jacoco()
            }
        }
    }

    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed :('
        }
    }
}