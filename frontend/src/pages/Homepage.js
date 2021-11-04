import * as React from 'react'
import styled from 'styled-components'
import { Typography } from '@mui/material'

export default function Homepage() {
  return (
    <HomepageContainer>
      <Title variant="h4">Zapliance EventListener</Title>
      <StyledCard>
        <Typography>
          Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
          nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat,
          sed diam voluptua. At vero eos et accusam et justo duo dolores et ea
          rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem
          ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur
          sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et
          dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam
          et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea
          takimata sanctus est Lorem ipsum dolor sit amet.
        </Typography>
      </StyledCard>
    </HomepageContainer>
  )
}
const StyledCard = styled.section`
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  align-items: center;
  padding: 25px;
  margin: 60px;
`

const Title = styled(Typography)`
  margin: 10px;
`

const HomepageContainer = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`
