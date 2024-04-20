using System;
using System.IO;
using System.Linq;
using System.Runtime.Serialization.Formatters.Binary;

[Serializable]
class CustomComparer : IComparer<string>
{
    public int Compare(string x, string y)
    {
        int lengthComparison = x.Length.CompareTo(y.Length);
        if (lengthComparison != 0)
        {
            return lengthComparison;
        }
        else
        {
            return string.Compare(x, y);
        }
    }
}


class Program
{
    static string attribute(FileSystemInfo name)
    {
        String atribute = "";
        FileAttributes attributes = File.GetAttributes(name.FullName);
        if ((attributes & FileAttributes.ReadOnly) == FileAttributes.ReadOnly)
        {
            atribute += "r";
        }
        else
        {
            atribute += "-";
        }

        if ((attributes & FileAttributes.Archive) == FileAttributes.Archive)
        {
            atribute += "a";
        }
        else
        {
            atribute += "-";
        }
        if ((attributes & FileAttributes.Hidden) == FileAttributes.Hidden)
        {
            atribute += "h";
        }
        else
        {
            atribute += "-";
        }
        if ((attributes & FileAttributes.System) == FileAttributes.System)
        {
            atribute += "s";
        }
        else
        {
            atribute += "-";
        }
        return atribute;
    }

    static int WypiszPlikiKatalogow(String temp, String docPath, ref DateTime najstarszyplik)
    {
        DirectoryInfo di = new DirectoryInfo(docPath);
        DirectoryInfo[] directories = di.GetDirectories("*", SearchOption.TopDirectoryOnly);
        FileInfo[] files = di.GetFiles("*", SearchOption.TopDirectoryOnly);


        foreach (DirectoryInfo dir in directories)
        {
            String attributes = attribute(dir);
            int itemCount = dir.GetDirectories("*", SearchOption.TopDirectoryOnly).Length + dir.GetFiles("*", SearchOption.TopDirectoryOnly).Length;
            Console.WriteLine(temp + dir.Name + "(" + itemCount + ") " + attributes);
            if (dir.CreationTime < najstarszyplik)
            {
                najstarszyplik = dir.CreationTime;
            }
            int liczbaplikow = WypiszPlikiKatalogow(temp + "   ", dir.FullName, ref najstarszyplik);

        }
        foreach (FileInfo file in files)
        {
            String attributes = attribute(file);
            Console.WriteLine(temp + file.Name + " " + file.Length + " bajtow " + attributes);
            if (file.CreationTime < najstarszyplik)
            {
                najstarszyplik = file.CreationTime;
            }
        }

        return directories.Length + files.Length;
    }

    static void Serialize<T>(T obj, string filePath)
    {
        var formatter = new BinaryFormatter();
        using (var stream = new FileStream(filePath, FileMode.Create))
        {
            formatter.Serialize(stream, obj);
        }
    }

    static T Deserialize<T>(string filePath)
    {
        var formatter = new BinaryFormatter();
        using (var stream = new FileStream(filePath, FileMode.Open))
        {
            return (T)formatter.Deserialize(stream);
        }
    }

    static void Main(string[] args)
    {
        if(args.Length == 0)
        {
            return;
        }
        string docPath = args[0];
        DateTime najstarszyplik = DateTime.MaxValue;
        DirectoryInfo di = new DirectoryInfo(docPath);
        int itemCount = di.GetDirectories("*", SearchOption.TopDirectoryOnly).Length + di.GetFiles("*", SearchOption.TopDirectoryOnly).Length;
        String attributes = attribute(di);
        Console.WriteLine(di.Name + "(" + itemCount + ") " + attributes);
        WypiszPlikiKatalogow("  ", docPath, ref najstarszyplik);
        Console.WriteLine();
        Console.WriteLine("Najstarszy plik: " + najstarszyplik.ToString());


        DirectoryInfo[] directories = di.GetDirectories("*", SearchOption.TopDirectoryOnly);
        FileInfo[] files = di.GetFiles("*", SearchOption.TopDirectoryOnly);
        SortedDictionary<string, long> directoryContents = new SortedDictionary<string, long>(new CustomComparer());
        foreach (var f in files)
        {
            directoryContents.Add(f.Name, f.Length);
        }
        foreach (DirectoryInfo dir in directories)
        {
            itemCount = dir.GetDirectories("*", SearchOption.TopDirectoryOnly).Length + dir.GetFiles("*", SearchOption.TopDirectoryOnly).Length;
            directoryContents.Add(dir.Name, itemCount);
        }


        Serialize(directoryContents, "data.bin");
        var deserializedDirectoryContents = Deserialize<SortedDictionary<string, long>>("data.bin");

        // Wyświetlamy zawartość kolekcji po deserializacji
        Console.WriteLine();
        foreach (var item in deserializedDirectoryContents)
        {
            Console.WriteLine(item.Key + " -> " + item.Value);
        }


    }
}