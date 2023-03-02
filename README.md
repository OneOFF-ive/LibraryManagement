# LibraryManagement
Java基础语法练习&&gradle的基础使用方法练习  

2023/2/26 该项目完成了最基本的功能，实现了用命令行指令完成对书本的增删查改，数据用一个json储存。  

2023/2/28 该项目实现了插件化。程序可以动态的加载插件，只需要在jar包的同目录下创建plugins文件夹并且将插件放入其中，程序会动态加载插件。  
程序预留了开发插件的接口：  
```java
public interface PluginService {
    void server(MyCliHandle handle);
}
```  
同时插件需要打包一份plugInfo.json文件  
```json
{
  "name": "Library Display",
  "mainClass": "library.plugin.LibraryDisplayPlugin"
}
```  
其中name表示插件名称，mainClass表示主类名称  
  
2023/3/1 项目功能基本实现，接下来将为该项目开发插件[LibraryManagement_plugins](https://github.com/OneOFF-ive/LibraryManagement_plugins)

2023/3/2 项目重构，抽象出数据接入层和数据处理层，使该项目可以适配任何数据库，用户可以根据自己的需求开发插件，使该项目使用自定义的数据来源。  
  
数据接入层接口
```java
public interface DataAccess {
   void insertData(Book data);
   void removeData(String isbn);
   List<Book> getDataBy(String field, Object condition);
   void updateData(Book data);
   void saveData();
   void readData();
}
```

数据处理层接口  
```java
public interface BookManager {
    void insertBook(Book book);
    void removeBook(String isbn);
    List<Book> seekBook(String prompt);
    void returnBook(String isbn);
    void rentBook(String isbn);
    void startup();
    void shutdown();
    DataAccess getDataAccess();
    void setDataAccess(DataAccess dataAccess);
} 
```  
可以使用不同的数据源实现DataAccess接口，并通过BookManage的setDataAccess更改项目的数据源；也可以根据需求实现BookManager，使用Application的setBookManager修改项目数据处理层，实现更多功能。

