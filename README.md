# SubX
A subtitle system that can display subtitles over presentations and control each of them independently.

## TOC
1. [What is SubX ?](https://github.com/mhmoodlan/SubX/new/master?readme=1#what-is-subx-)
2. [Getting Startrd](https://github.com/mhmoodlan/SubX/new/master?readme=1#getting-started)
3. [How does it work ?](https://github.com/mhmoodlan/SubX/new/master?readme=1#how-does-it-work-)
4. [Built With](https://github.com/mhmoodlan/SubX/new/master?readme=1#built-with)
5. [Authors](https://github.com/mhmoodlan/SubX/new/master?readme=1#authors)
6. [License](https://github.com/mhmoodlan/SubX/new/master?readme=1#license)
7. [Acknowledgments](https://github.com/mhmoodlan/SubX/new/master?readme=1#acknowledgments)

## What is SubX ?
A desktop application that can be used in events and conferences to provide subtitles/captions to the foreign audience without the need of another screen or projector also it separates the presentation control (done by the speaker) from the subtitles control (done by a staff member).
SubX displays subtitls over presentations (powerpoint, etc...) without preventing the speaker from controlling his/her slides by providing a subtitle control panel that can be accessed through another computer.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites (Developing & installing)

First you need java (1.8+) installed on your machine,

You can check [wikihow](http://www.wikihow.com/Install-the-Java-Software-Development-Kit)'s guide on installing java on windows, mac and linux.

Or instead you can check [oracle's guide](https://docs.oracle.com/javase/8/docs/technotes/guides/install/windows_jdk_install.html) :frog:

You can check if java is installed by opening a terminal ( [windows](http://www.wikihow.com/Open-the-Command-Prompt-in-Windows), [mac](http://www.wikihow.com/Open-a-Terminal-Window-in-Mac), [linux](http://www.wikihow.com/Open-a-Terminal-Window-in-Ubuntu) ) and excuting the command :
```
java -version
```
This will give you the java version installed on your machine (which means you are good to go :thumbsup:) -*this project was built upon java 1.8 so you may want to install the same or a newer version*.

### Development Environment

This is a [netbeans](https://netbeans.org/) project so if you're already using it you ready to go.

If you're using a different java IDE you can easily copy classes from [scr](https://github.com/mhmoodlan/SubX/tree/master/src).

## How does it work ?
1. First you need java see [Prerequisites](https://github.com/mhmoodlan/SubX/new/master?readme=1#prerequisites-developing--installing).

2. Next [download SubX](https://github.com/mhmoodlan/SubX/releases/latest)

3. Extract the SubX-vx.x.rar file and you will find the following files :

* SubX Server.jar (the server program)
* SubX Client.jar (the client program)
* SubXServer.ini (the server's configuration file)
* SubXClient.ini (the client's configuration file)
* defaultSubtitle.txt (the subtitles file loaded by default)
  
4. Subx lets you run presentation slides and display subtitles on the same screen and control each of them independently. To do that the program is divided into two parts :
    1. SubX Client : displays the subtitles. (on the same computer as the slides)
    
    2. SubX Server : controls the subtitles.
    
    ![description of the two parts](https://github.com/mhmoodlan/SubX/blob/master/img/desc-1.jpg)
    
    <sub> Laptop vector [Designed by Layerace / Freepik](http://www.freepik.com) </sub>
    1. The first computer will display the slides and the subtitles : 
      
          must contain _**SubX Client.jar**_ and _**SubXClient.ini**_ files -make sure that you put them in the **SAME** folder.
         
    2. The second computer will control the subtitle : 
    
          must contain _**SubX Server.jar**_, _**SubXServer.ini**_ and _**defaultSubtitle.txt**_ files -make sure that you put them in the **SAME** folder.
    
    _NOTE_ : controlling slides could be done by the speaker using a remote controller or by using the first computer.

5. Connect the two parts :
    1. The two computers must be on the same network (WIFI, LAN, etc...)
    2. The Server (second computer) must have a fixed IP address keep this IP we'll need it later --see how to set a static IP for your computer on [windows](https://www.howtogeek.com/howto/19249/how-to-assign-a-static-ip-address-in-xp-vista-or-windows-7), [mac](https://www.howtogeek.com/howto/22161/how-to-set-up-a-static-ip-in-mac-os-x) and [linux](https://www.howtoforge.com/linux-basics-set-a-static-ip-on-ubuntu).
    3. The _**SubXServer,ini**_ file would look like this :
    
        ```
        port = 6666
        defaultFile = defaultSubtitle.txt
        ```
        The _port = 6666_ line defines the port the server is listening to which will be used by clients to connect to the server.
        
        The _defaultFile = defaultSubtitle.txt_ defines the default loaded subtitles file (you can change this to what ever subtitles file you want but make sure to put it in the same folder and apply to the [subtitle file format](https://github.com/mhmoodlan/SubX/new/master?readme=1#subtitle-file-format)).
     
     4. The client computer connects with the server by reading the _**host**_ and the _**port**_ values from the _**SubXClient.ini**_, example :
        
        ```
        host = 127.0.0.1
        port = 6666
        ```
        The _host = 127.0.0.1_ refers to the static IP address of the server (here you should replace _127.0.0.1_ with your static IP defined in step ii ).
        
        The _port = 6666_ defines the port that the server is listening to. (if you changed it in the _**SubXServer.ini**_ you should change it here too).
        
6. After setting the IP, you can now run the _**SubX Server.jar**_ on the server computer and _**SubX Client.jar**_ on the client computer and you're ready to go. :tada:

**IMPORTANT NOTE :** To give the speaker the ability to control the slides while the _**SubX Client.jar**_ is running make sure to return the focus to the slides by clicking on the presentation program (the subtitles will stay on top).

### Subtitle File Format
The format is very simple put your subtitles in a plain text file (NOT a word processor) and separate every sentence you want to appear individually by a one line, example :

```
The first line this will appear in a separate line
The second line this also will be displayed individually
and so on...
```
_NOTE :_ The subtitle bar can be customized by changing its width, height, position and font size from the server control panel.

## Built With
* [NetBeans](https://netbeans.org/) - The java IDE

## Authors

* **Mahmoud Aslan** - *Initial work* - [mhmoodlan](https://github.com/mhmoodlan)

See also the list of [contributors](https://github.com/mhmoodlan/SubX/graphs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

This project was initially developed to be used in [TedxMimasStreet](http://www.tedxmimasstreet.com) main event to make its talks more accessible to the foreign audience.
