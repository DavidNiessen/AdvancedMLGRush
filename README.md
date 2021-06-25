# AdvancedMLGRush

This is the source code of the AdvancedMLGRush Minecraft plugin by SkillCode. 
Make sure to read LICENSE.txt

To import the source code without errors you have to do the following things first:
<ol>
  <li>Install the Spigot <a href="https://www.spigotmc.org/wiki/buildtools/#1-13">BuildTools</a> for version 1.13</li>
  <li>Create the "settings.xml" file in your .m2 directory if it does not already exist. Create the variable "output-mlgrush" and set it to the path where you want to export the plugin. A bit further below you will find an example settings.xml file</li>
</ol>

```xml
<settings>

  <profiles>
    <profile>
      <id>mlgrush</id>
      <properties>
        <output-mlgrush>YOUR PATH</output-mlgrush>
      </properties>
    </profile>
  </profiles>
 
  <activeProfiles>
    <activeProfile>mlgrush</activeProfile>
  </activeProfiles>
</settings>

```

Support: https://discord.skillplugins.com -> mlgrush-src-help
<br>Wiki: https://github.com/SkillC0de/AdvancedMLGRush-Wiki/wiki

