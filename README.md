# NLP-TextCategorization

###Data to use: movie review data available from http://www.cs.cornell.edu/People/pabo/movie-review-data/

Please use polarity dataset v2.0: 1000 positive and 1000 negative processed reviews.

###Reference paper:
```A Sentimental Education: Sentiment Analysis Using Subjectivity Summarization Based on Minimum Cuts'', Proceedings of the ACL, 2004.```
There are many papers that have used this data set since then.
What you need to do:
1. Implement naïve Bayes classifier. There are two parts you need to do:
* Training:your program will take two lists of files:one containing all of positive review files, the other containing the negative ones, and output a model file.
b) Testing and evaluation: your program will take a model file and two lists of files: positive and negative review lists, and output the classification accuracy for the test set.
* Use your own naïve Bayes classifier to run a 10-fold cross validation classification for the data set and report the overall performance.
####Note: 
* what’s 10-fold cross validation? You split the data into ten subsets, use one subset as test set, and the other 9 subsets for training; you do this for each of the subsets and report an average performance of the 10 runs.
* How to split the data? You will use the same setup as used in the paper:
```fold 1: files tagged cv000 through cv099, in numerical order 
fold 2: files tagged cv100 through cv199, in numerical order ...
fold 10: files tagged cv900 through cv999, in numerical order```
3. Writeareportaboutyourexperimentsandfindings.

##Tools
* WEKA
* SVM
