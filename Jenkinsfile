pipeline {
	agent any

	environment {
		GITHUB_CREDENTIALS = credentials('github-token') // GitHub token
		DOCKER_IMAGE = "my-app"                          // Docker image name (without tag)
		DOCKER_TAG = "latest"                            // Docker image tag
		DOCKER_REGISTRY = "docker.io"                    // Docker registry (Docker Hub)
	}

	stages {
		stage('Checkout') {
			steps {
				script {
					echo 'Checking out code from GitHub...'
					checkout scm  // This automatically uses the branch that triggered the build

					// Alternative: Explicitly specify the branch
					// git branch: 'develop',
					//     url: 'https://github.com/y123-cmd/bank',
					//     credentialsId: 'github-token'
				}
			}
		}

		stage('Verify Docker') {
			steps {
				echo 'Verifying Docker installation...'
				sh '''
                if ! command -v docker &> /dev/null; then
                   echo "Docker is not installed or not in PATH"
                   exit 1
                fi
                docker --version
             '''
			}
		}

		stage('Build Docker Image') {
			steps {
				echo 'Building Docker image...'
				sh '''
                docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                docker images | grep ${DOCKER_IMAGE}
             '''
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
                   echo "Logging into Docker registry..."
                   echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin $DOCKER_REGISTRY

                   echo "Tagging image..."
                   docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_REGISTRY}/${DOCKER_USER}/${DOCKER_IMAGE}:${DOCKER_TAG}

                   echo "Pushing image..."
                   docker push ${DOCKER_REGISTRY}/${DOCKER_USER}/${DOCKER_IMAGE}:${DOCKER_TAG}

                   echo "Image pushed successfully!"
                '''
				}
			}
		}

		stage('Test') {
			steps {
				echo 'Running tests...'
				script {
					// Add your test commands here
					// Examples:
					// sh 'pytest tests/'
					// sh 'npm test'
					// sh 'mvn test'
					echo 'No tests configured yet. Add your test commands here.'
				}
			}
		}
	}

	post {
		always {
			echo 'Cleaning up Docker resources...'
			sh '''
             docker logout || true
             docker system prune -f || true
          '''
		}
		success {
			echo '✅ Pipeline completed successfully!'
			echo "Docker image pushed: ${DOCKER_REGISTRY}/${env.DOCKER_USER}/${DOCKER_IMAGE}:${DOCKER_TAG}"
		}
		failure {
			echo '❌ Pipeline failed! Check the logs above for details.'
		}
	}
}