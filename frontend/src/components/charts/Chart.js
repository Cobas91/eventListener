import * as React from 'react'

import styled from 'styled-components'
import SimpleBarChart from './SimpleBarChart'
import SimplePieChart from './SimplePieChart'

export default function Chart({ variant, data }) {
  return (
    <StatistikContainer>
      <ChooseChart variant={variant} data={data} />
    </StatistikContainer>
  )
}
function ChooseChart({ variant, data }) {
  if (variant === 'SimpleBarChart') {
    return <SimpleBarChart barChartData={data} />
  }
  if (variant === 'PieChart') {
    return <SimplePieChart data={data} />
  } else {
    return 'No valid Chart'
  }
}
const StatistikContainer = styled.section`
  display: flex;
  justify-content: center;
  width: 80%;
  height: 350px;
`
