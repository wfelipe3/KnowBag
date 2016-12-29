using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace AsyncAwaitHelloWorld
{
    public class Program
    {
        public static void Main(string[] args)
        {
            AsyncAwaitHelloWorld.PrintCurrentThread("A");
            var value = new AsyncAwaitHelloWorld().HelloWorldAsync();
            Task.Delay(2000).Wait();
            AsyncAwaitHelloWorld.PrintCurrentThread("B");
        }
    }

    class AsyncAwaitHelloWorld
    {
        public async Task HelloWorldAsync()
        {
            Task<int> longRunningTask = LongRunningOperationAsync();

            Task<int> value = longRunningTask.ContinueWith(t => 10);

            int result = await longRunningTask;
            Console.WriteLine($"the value is {result}");
            PrintCurrentThread("D");
        }

        public async Task<int> LongRunningOperationAsync()
        {
            await Task.Delay(1000);
            PrintCurrentThread("C");
            return 1;
        }

        public static void PrintCurrentThread(string counter)
        {
            Console.WriteLine($"{counter}) the current thread is {Thread.CurrentThread.ManagedThreadId}");
        }

    }
}
