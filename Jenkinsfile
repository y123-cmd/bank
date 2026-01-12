pipeline {
	agent any

	environment {
		GITHUB_CREDENTIALS = credentials('github-token') // GitHub token
		DOCKER_IMAGE = "my-app:latest"                   // Docker image name
		DOCKER_REGISTRY = "docker.io"                    // Docker registry (Docker Hub)
	}

	stages {
		stage('Checkout') {
			steps {
				git(
				    git branch : 'develop',
					url: 'https://github.com/y123-cmd/bank',//yess
					credentialsId: 'github-token'
				)
			}
		}

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
					credentialsId: 'docker-hub-credentials',  // Jenkins Docker credentials ID
					usernameVariable: 'DOCKER_USER',
					passwordVariable: 'DOCKER_PASS'
				)]) {
					sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin $DOCKER_REGISTRY
                        docker tag $DOCKER_IMAGE $DOCKER_REGISTRY/$DOCKER_USER/$DOCKER_IMAGE
                        docker push $DOCKER_REGISTRY/$DOCKER_USER/$DOCKER_IMAGE
                    '''
				}
			}
		}

		stage('Test') {
			steps {
				echo 'Running tests (if any)...'
				// Example: sh 'pytest' or any test command
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
