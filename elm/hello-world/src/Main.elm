module Main exposing (..)

import Html exposing (Html, button, div, text)
import Html.Events exposing (onClick)
import HtmlHelloWorld exposing (..)


main =
  Html.beginnerProgram { model = model, view = view, update = update }
