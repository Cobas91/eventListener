/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import * as React from 'react'

import styled from 'styled-components'
import SimpleBarChart from './SimpleBarChart'
import SimplePieChart from './SimplePieChart'

export default function Chart({ variant, data }) {
  return (
    <StatisticContainer>
      <ChooseChart variant={variant} data={data} />
    </StatisticContainer>
  )
}
function ChooseChart({ variant, data }) {
  if (variant === 'SimpleBarChart') {
    return <SimpleBarChart barChartData={data} />
  }
  if (variant === 'PieChart') {
    return <SimplePieChart simpleBarChartData={data} />
  } else {
    return 'No valid Chart'
  }
}
const StatisticContainer = styled.section`
  display: flex;
  justify-content: center;
  width: 80%;
  height: 350px;
`
