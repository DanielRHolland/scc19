import Layout from '../components/MyLayout';
import Link from 'next/link';
import fetch from '../node_modules/isomorphic-unfetch';

const Index = props => (
  <Layout>
    <h1>Shares</h1>
    <table>
      <tr>
        <th>Symbol</th>
        <th>Name</th>
        <th>Shares Available</th>
        <th>Price</th>
      </tr>
      {props.shares.map(share => (       	
        <tr>
           <td> {share.companySymbol} </td>
           <td> {share.companyName} </td>
           <td> {share.numberOfSharesAvailable} </td>
           <td> {share.sharePrice.currency} {share.sharePrice.value} </td>
        </tr>
      ))}
    </table>
  </Layout>
);

Index.getInitialProps = async function() {
  const res = await fetch('http://localhost:8080/share/list');
  const data = await res.json();
  console.log(`data fetched. Count: ${data.length}`);
  console.log(data)
  return {
      shares: data
    };
};

export default Index;
