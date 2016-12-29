using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using TLE.model;

namespace TLE.Controllers
{
    [Route("api/[controller]")]
    public class ValuesController : Controller
    {

        private TleContext _context;

        public ValuesController(TleContext context)
        {
            _context = context;
        }

        // GET api/values
        [HttpGet]
        public IEnumerable<Tle> Get()
        {
            return _context.Tle.AsEnumerable();
        }

        // GET api/values/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        // POST api/values
        [HttpPost]
        public void Post([FromBody]string value)
        {
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
