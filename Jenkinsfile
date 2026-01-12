pipeline {
	agent any

	environment {
		DOCKER_IMAGE = "my-app:latest"
		DOCKER_REGISTRY = "docker.io"
	}

	stages {

		stage('Build Docker Image') {
			steps {
				echo 'Building Docker image...'
				sh 'docker build -t $DOCKER_IMAGE .'
			}
		}

		stage('Push Docker Image') {
			steps {
				echo 'Pushing Docker image to registry...'
				withCredentials([usernamePassword(
					credentialsId: 'docker-hub-credentials',
					usernameVariable: 'DOCKER_USER',
					passwordVariable: 'DOCKER_PASS'
				)]) {
					sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker tag $DOCKER_IMAGE $DOCKER_USER/$DOCKER_IMAGE
                        docker push $DOCKER_USER/$DOCKER_IMAGE
                    '''
				}
			}
		}

		stage('Test') {
			steps {
				echo 'Running tests (if any)...'
			}
		}
	}

	post {
		always {
			echo 'Cleaning up Docker resources...'
			sh 'docker system prune -f || true'
		}
		success {
			echo 'Pipeline completed successfully!'
		}
		failure {
			echo 'Pipeline failed!'
		}
	}
}
