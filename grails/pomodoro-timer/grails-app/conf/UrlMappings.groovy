class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "/user"(method:"POST", controller:"User", action: "createUser")
        "/user/${name}"(method:"GET", controller:"User", action: "getUserByName")
	}
}
