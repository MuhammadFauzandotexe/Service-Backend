pipeline {
    agent {
        docker {
            image 'maven:3.8.5-openjdk-17-slim' // Docker image Maven dengan JDK 17
            args '-v $HOME/.m2:/root/.m2' // Mount local Maven cache agar lebih cepat
        }
    }
    stages {
        stage('Checkout Code') {
            steps {
                // Clone repository
                git branch: 'main', url: 'https://github.com/MPernandes/Service-Backend.git'
            }
        }

        stage('Build') {
            steps {
                // Build proyek menggunakan Maven
                echo 'Building project with Maven...'
                sh 'mvn clean package -DskipTests' // Build tanpa menjalankan test
            }
        }

        stage('Test') {
            steps {
                // Jalankan pengujian otomatis
                echo 'Running tests...'
                sh 'mvn test' // Jalankan unit tests
            }
        }

        stage('Package') {
            steps {
                // Package file JAR/war jika diperlukan
                echo 'Packaging application...'
                sh '''
                if [ -f target/*.jar ]; then
                    echo "JAR file found:"
                    ls target/*.jar
                else
                    echo "No JAR file generated. Please check the build process."
                    exit 1
                fi
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline selesai dijalankan.'
        }
        success {
            echo 'Pipeline berhasil!'
        }
        failure {
            echo 'Pipeline gagal.'
        }
    }
}