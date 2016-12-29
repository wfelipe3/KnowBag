using System;
using System.IO;
using Monad;
using Monad.Utility;

namespace CSharpMonadsHelloWorld
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var result = from tmpFileName in GetTempFileName()
                from _ in WriteFile(tmpFileName, "sldkfjsdlj")
                from dataFromFile in ReadFile(tmpFileName)
                from __ in DeleteFile(tmpFileName)
                select dataFromFile;

            Console.WriteLine(result.Invoke());
        }

        private static IO<Unit> DeleteFile(string tmpFileName)
        {
            return () => Unit.Return(() => File.Delete(tmpFileName));
        }

        private static IO<string> ReadFile(string tmpFileName)
        {
            return () => File.ReadAllText(tmpFileName);
        }

        private static IO<Unit> WriteFile(string tmpFileName, string data)
        {
            return () => Unit.Return(() => File.WriteAllText(tmpFileName, data));
        }

        private static IO<string> GetTempFileName()
        {
            return Path.GetTempFileName;
        }
    }
}