```bash
cd ..
mkdir LibraryManagementSys
cd LibraryManagementSys
mkdir -p src/main/java/library/model
mkdir -p src/main/java/library/service
mkdir -p src/main/java/library/repository
mkdir -p src/main/java/library/exception
mkdir -p src/main/java/library/ui
javac -d out $(find src -name "*.java")
java -cp out library.Main

📚Library Management System
```




