Feature: Rest API: generateScatterGram

  Scenario: allParamAreInitalizedProperly
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find the content OK

  Scenario: allParamAreInitalizedProperlyDotRgbToken
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94&87&130&81  | 94&87&130&81 | 255&255&0\|0&255&255\|255&255&0\|20&255&255 | ICAScattergramSrv/perdatter300D.png | &     | \|         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find the content OK

  Scenario: allParamAreInitalizedProperly&tokenSLASHRgbToken
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#255#0.0#255#255.255#255#0.20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | .         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find the content OK

  Scenario: emptyBody
    Given I send an empty body
    When I send the request to generateScatterGram
    Then I should be able to find the content is not OK

  Scenario: wrongToken
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | $     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK : the x, y and color array are malformed, the tokens are not well defined'

  Scenario: wrongRgbToken
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | $         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK : the x, y and color array are malformed, the tokens are not well defined'

  Scenario: NoPermissionsToWriteTheFile
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | WE:/fharts/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK : no privileges to write file:

  Scenario: RGBArrayHasSeveralPositionsUncompleted
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#255#0,0#255#255,255#0,20#255#255     | ICAScattergramSrv/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK :Error generating the axisInvalid number of parameters in a position of the RGB array'

  Scenario: differentNumerOfXandYPoints
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130     | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK : the x, y and color array don't have the same length'

  Scenario: differentNumerOfXYandColorPoints
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#12  | 94#87#130#81 | 255#255#0,0#255#255,255#255#0            | ICAScattergramSrv/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK : the x, y and color array don't have the same length'

  Scenario: noXData
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      |               | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK : the x, y and color array are malformed, the tokens are not well defined'


  Scenario: RGBarrayCointainsNonNumbers
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#b#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | ,         |       300    | 300      |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK :Error generating the axisFor input string: "b"'

  Scenario: xArrayCointainsNonNumbers
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#b#130#81  | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | ,          | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK :Error generating the axisFor input string: "b"'

  Scenario: yArrayCointainsNonNumbers
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#b#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | ,          | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK :Error generating the axisFor input string: "b"'

  Scenario: yArrayCointainsEmptyPossitions
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94###81      | 255#255#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK :Error generating the axisempty String'

  Scenario: xArrayCointainsEmptyPossitions
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94###81  | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255      | ICAScattergramSrv/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK :Error generating the axisempty String'

  Scenario: colorArrayCointainsEmptyPossitions
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#255#0,,,20#255#255                   | ICAScattergramSrv/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK :Error generating the axisInvalid number of parameters in a position of the RGB array'

  Scenario: ImageSizeNotDefined
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | ICAScattergramSrv/perdatter300D.png | #     | ,         |           |           |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK : the size of the image is not correctly  defined'

