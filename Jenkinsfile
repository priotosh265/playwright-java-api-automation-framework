pipeline {
    agent any

    tools {
        maven 'Maven 3'
        jdk 'JDK 17'
    }

    environment {
        BASE_URL = 'https://restful-booker.herokuapp.com'
    }

    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling project and resolving dependencies...'
                bat 'mvn clean compile test-compile'
            }
        }

        stage('Execute API Tests') {
            steps {
                echo 'Running Playwright Java API automation test suite...'
                bat "mvn test -Dbase.url=${env.BASE_URL}"
            }
        }
    }

    post {
        always {
            echo 'Archiving test reports...'
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
        }
        success {
            echo 'All Playwright API automation tests executed successfully!'
        }
        failure {
            echo 'API automation tests failed. Please inspect the test execution logs.'
        }
    }
}
