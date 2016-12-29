using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;

namespace TLE.model {
    public class TleContext : DbContext
    {

        public TleContext(DbContextOptions<TleContext> options) : base(options) { }

        public DbSet<Tle> Tle {get; set;}
    }

    public class Tle
    {
        public string Message{get; set;}
        public int Id {get; set;}
    }
}