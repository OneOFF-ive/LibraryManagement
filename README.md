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
