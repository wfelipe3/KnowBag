package com.knowbag.git

import java.nio.file.{Files, Paths}

import org.apache.commons.io.FileUtils
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers}

/**
  * Created by dev-williame on 1/3/17.
  */
class GitTest extends FlatSpec with Matchers with BeforeAndAfter {

  val path = Files.createTempDirectory("git")

  "git clone" should "download repository" in {
    Git.cloneRepository()
      .setCredentialsProvider(new UsernamePasswordCredentialsProvider("wfelipe3", "Wfr82636"))
      .setURI("https://bizagidev.visualstudio.com/DefaultCollection/ToolsAndLibs/_git/RNF")
      .setDirectory(path.toFile)
      .call()

    println(path)
  }

  "remove path" should "remove tmp file" in {
    FileUtils.deleteDirectory(Paths.get("/var/folders/r2/7191hr6s29928w2h7m66f_hr0000gn/T/git2221978462194069822").toFile)
  }
}
