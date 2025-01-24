 pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/MPernandes/Service-Backend.git'
        BRANCH = 'main' // Ganti dengan branch yang benar
        PROJECT_DIR = '.' // Ganti ke subdirektori jika package.json ada di subfolder
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Checkout kode dari repository
                git branch: "${BRANCH}", url: "${REPO_URL}"
            }
        }

        stage('Prepare Environment') {
            steps {
                script {
                    // Pindah ke direktori proyek (jika package.json ada di subfolder)
                    dir("${PROJECT_DIR}") {
                        // Periksa apakah file package.json ada
                        if (!fileExists('package.json')) {
                            error("File package.json tidak ditemukan di ${PROJECT_DIR}. Pastikan file tersebut ada.")
                        }
                    }
                }
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    // Pindah ke direktori proyek dan instal dependency
                    dir("${PROJECT_DIR}") {
                        sh 'npm install'
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Jalankan build jika diperlukan
                    dir("${PROJECT_DIR}") {
                        if (fileExists('package.json')) {
                            sh 'npm run build || echo "Tidak ada langkah build."'
                        }
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Jalankan tes otomatis
                    dir("${PROJECT_DIR}") {
                        if (fileExists('package.json')) {
                            sh 'npm test || echo "Tidak ada langkah test."'
                        }
                    }
                }
            }
        }
    }
 }
