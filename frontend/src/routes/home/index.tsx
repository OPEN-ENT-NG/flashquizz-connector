import React from "react";

import { ID } from "edifice-ts-client";

export interface AppProps {
  _id: string;
  created: Date;
  description: string;
  map: string;
  modified: Date;
  name: string;
  owner: { userId: ID; displayName: string };
  shared: any[];
  thumbnail: string;
}

export const Home: React.FC = () => {
  return (
    <div>
      <h1>Home</h1>
    </div>
  );
};
