### FINAL PROJECT

Member: htn279, bab631

Data: in hdfs, retrieved using flume (config and start script included in /data_ingest)

-   to start flume do

```
chmod 777 fluming.sh
./fluming.sh
```

-   to start use <ctrl + c>

Profiling: using hivesql

-   check hive.txt for queries (need permission from hpc to add serde)

Analysing:

-   use code in /ana_code

```
chmod 777 run_final.sh
./run_final.sh
```

Acknowledgement:
[serdes snapshot](https://github.com/KhareS/Twitter-Data-Analysis-Using-Flume-Hive/tree/master/lib)
[afinn-111 dict for sentiment analysis](https://github.com/fnielsen/afinn/blob/master/afinn/data/AFINN-111.txt)
[idea and help with flume](http://ijariie.com/AdminUploadPdf/Twitter_Data_Analysis_using_Hadoop_ijariie9093.pdf)
[horton-works](http://hortonworks.com/hadoop-tutorial/how-to-refine-and-visualize-sentiment-data/)
[github](https://gist.github.com/umbertogriffo/a512baaf63ce0797e175)
