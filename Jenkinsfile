pipeline {
    agent any

    tools {
        jdk 'jdk21'
        maven 'maven3'
    }

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['smoke', 'regression'],
            description: 'Select test suite to execute'
        )
    }

    environment {
        MAVEN_OPTS = '-Xmx1024m'
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/niteshjain007/selenium-cucumber-testNG-project.git'
            }
        }

        stage('Build & Execute Tests') {
            steps {
                script {
                    if (params.TEST_SUITE == 'smoke') {
                        sh 'mvn clean test -Dcucumber.filter.tags="@Smoke"'
                    } else {
                        sh 'mvn clean test -Dcucumber.filter.tags="@Regression"'
                    }
                }
            }
        }

        stage('Publish TestNG Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Publish Cucumber HTML Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'target/cucumber-reports',
                    reportFiles: 'index.html',
                    reportName: 'Cucumber Test Report',
                    keepAll: true,
                    alwaysLinkToLastBuild: true
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/**/*.html', fingerprint: true
        }

        success {
            emailext(
                subject: "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <p>Build Successful</p>
                <p>Job: ${env.JOB_NAME}</p>
                <p>Build Number: ${env.BUILD_NUMBER}</p>
                <p>Report: <a href="${env.BUILD_URL}Cucumber_Test_Report/">View Report</a></p>
                """,
                mimeType: 'text/html',
                to: 'team1@mail.com,team2@mail.com'
            )
        }

        failure {
            emailext(
                subject: "FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <p>Build Failed</p>
                <p>Job: ${env.JOB_NAME}</p>
                <p>Build Number: ${env.BUILD_NUMBER}</p>
                <p>Console: <a href="${env.BUILD_URL}console">Logs</a></p>
                """,
                mimeType: 'text/html',
                to: 'nitesh.iiitm@gmail.com'
            )
        }
    }
}
