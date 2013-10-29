#Zeppelin



**Zeppelin** is complete large scale data analysis environment, including

   * Web based GUI
   * With interactive visualization
   * Super easy SQL like analysis language called **ZQL**
   * Custom user routine support 
   * Central archive of library called **ZAN** (Zeppelin Archivce Network)
   * On top of Hive (or any Hive compatible system like Spark)

Learn more | User Guide | Screenshots



**Zeppelin-core** is an application framework for Java developers to simplify large scale data analysis by leveraging HiveQL.

There're some project that sharing same value. For example Cascading, Crunch, Sqoobi, etc.
Compare to them, Zeppelin-core is more focusing on easy-of-use by leveraging HiveQL. 


Learn more | User Guide | API Document | Examples






####Maven dependency


Note: Not yet released in public maven repository.
To use it, you need build from source and publish to local repository (mvn install)


####Maven dependency

##Release
      <dependency>
            <groupId>com.nflabs.zeppelin</groupId>
            <artifactId>zeppelin-core</artifactId>
            <packaging>jar</packaging>
            <version>0.1.0</version>
      </dependency>


###Build


      mvn clean package


###Packaging

      mvn assembly:assembly

The package is generated under __target__ directory

###Mailing list

[Developers](https://groups.google.com/forum/#!forum/zeppelin-developers) : https://groups.google.com/forum/#!forum/zeppelin-developers

[Users](https://groups.google.com/forum/#!forum/zeppelin-users) : https://groups.google.com/forum/#!forum/zeppelin-users


###License
[Apache2](http://www.apache.org/licenses/LICENSE-2.0.html) : http://www.apache.org/licenses/LICENSE-2.0.html






