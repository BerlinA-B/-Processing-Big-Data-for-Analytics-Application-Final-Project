javac -classpath `yarn classpath` -d . FinalMapper.java
javac -classpath `yarn classpath` -d . FinalReducer.java
javac -classpath `yarn classpath`:. -d . Final.java

jar -cvf Final.jar *.classpath

hadoop jar Final.jar Final /user/htn279/final/tweets-final /user/htn279/final/output/