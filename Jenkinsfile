pipeline {
    agent any

    tools {
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn clean compile -Dmaven.test.skip=true -Dspring.profiles.active=dev"
            }
        }
        stage('Test') {
            steps {
                sh "mvn test -Dspring.profiles.active=dev"
            }
        }
        stage('Deploy') {
            steps {
                sh "mvn clean heroku:deploy -Dmaven.test.skip=true -Dspring.profiles.active=prod"
            }
        }
    }
}
