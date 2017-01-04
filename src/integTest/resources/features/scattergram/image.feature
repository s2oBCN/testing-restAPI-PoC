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

  Scenario: NoPermissionsToWriteTheFile
    Given the following image has just come out:
      | xPoints       | yPoints      | colors                                   | path                        | token | rgbToken  | imgHeight | imgWidth  |
      | 94#87#130#81  | 94#87#130#81 | 255#255#0,0#255#255,255#255#0,20#255#255 | WE:/fharts/perdatter300D.png | #     | ,         | 300       | 300       |
    When I send the request to generateScatterGram
    Then I should be able to find an error of 'NACK : no privileges to write file:

