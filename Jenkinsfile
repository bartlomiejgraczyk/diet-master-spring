pipeline {
    agent any

    tools {
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn clean compile -Dmaven.test.skip=true"
            }
        }
        stage('Test') {
            steps {
                sh "mvn test -Denvironment=test"
            }
        }
        stage('Deploy') {
            steps {
                sh "mvn clean heroku:deploy -Dmaven.test.skip=true -Dspring.profiles.active=prod"
            }
        }
    }
}
