pipeline {
    agent {
        docker {
            image 'node:16-buster-slim' // Menggunakan image Node.js
            args '-p 3000:3000' // Mapping port jika diperlukan
        }
    }
    stages {
        stage('Install npm') {
            steps {
                sh '''
                # Periksa apakah npm tersedia
                if ! command -v npm >/dev/null 2>&1; then
                    echo "npm tidak ditemukan, pastikan Node.js sudah terpasang dalam image"
                    exit 1
                fi

                # Periksa package.json dan instal dependensi
                if [ -f package.json ]; then
                    echo "Installing dependencies..."
                    npm install
                else
                    echo "File package.json tidak ditemukan, pastikan file ada di repository"
                    exit 1
                fi
                '''
            }
        }
        stage('Build') {
            steps {
                echo 'Building project...'
                sh '''
                if [ -f package.json ]; then
                    npm run build || echo "No build script defined in package.json"
                else
                    echo "Skipping build: package.json not found"
                fi
                '''
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
                sh '''
                if [ -f ./jenkins/scripts/test.sh ]; then
                    chmod +x ./jenkins/scripts/test.sh
                    ./jenkins/scripts/test.sh
                else
                    echo "Test script ./jenkins/scripts/test.sh not found"
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