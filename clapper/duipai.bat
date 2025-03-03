:again
javac Rand.java Standard.java Main.java
java Rand > in.txt
java Main < in.txt > main_output.txt
java Standard < in.txt > standard_output.txt
fc main_output.txt standard_output.txt
if not errorlevel 1 goto again
pause
