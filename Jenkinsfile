pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'Compiling...'
        sh 'mvn compile'
      }
    }

    stage('Package & Push') {
      parallel {
        stage('Package App') {
          steps {
            echo 'Packaging...'
            sh 'mvn clean package -DskipTests'
          }
        }

        stage('Push Docker Image') {
          steps {
            script {
              docker.withRegistry('https://index.docker.io/v1/', 'dockerlogin') {
                def commitHash = env.GIT_COMMIT?.take(7) ?: sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                def dockerImage = docker.build("ductuanbl2000/be-sync-space-app:${commitHash}", "./")
                dockerImage.push()
                dockerImage.push("latest")
                dockerImage.push("dev")
              }
            }

          }
        }

      }
    }

    stage('Trigger Deploy') {
      steps {
        script {
          build job: 'DeploySyncSpace', wait: false
        }

      }
    }

  }
  tools {
    maven 'Maven 3.9.14'
  }
  post {
    always {
      echo 'This pipeline is completed.'
    }

  }
}